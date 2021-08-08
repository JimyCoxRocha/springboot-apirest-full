package com.mdw.replica.services.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdw.replica.config.JWTAuthentication;
import com.mdw.replica.entities.http.RSEntity;
import com.mdw.replica.entities.http.RSExceptionEntity;
import com.mdw.replica.entities.securitys.ItemMenuEntity;
import com.mdw.replica.entities.securitys.LoginInEntity;
import com.mdw.replica.entities.securitys.LoginInOutEntity;
import com.mdw.replica.entities.securitys.MenuEntity;
import com.mdw.replica.entities.securitys.ModuleEntity;
import com.mdw.replica.entities.securitys.OptionEntity;
import com.mdw.replica.entities.securitys.ProfileEntity;
import com.mdw.replica.entities.securitys.UserAuthenticatedEntity;
import com.mdw.replica.entities.securitys.UserEntity;
import com.mdw.replica.utilities.Helpers;

@Service
public class SecurityService {

	@Autowired
	private UserService userServi;
	
	@Autowired
	private ProfileService profileServi;
	
	@Autowired
	private OptionService optionServi;
	
	@Autowired
	private ModuleService moduleServi;
	
	@Autowired
	JWTAuthentication jwt;
	
	public RSEntity<LoginInOutEntity> login (LoginInEntity user) throws RSExceptionEntity{
		try {
			UserEntity _user = this.userServi.findByUsernameAndPassword(user);
			List<ProfileEntity> _menu = this.profileServi.findProfileByIdUser(_user.getIdUser(), true);
			List<ProfileEntity> _menuFilter = _menu.stream().filter(Helpers.distinctByKey(p -> p.getIdOption()))
					.collect(Collectors.toList());
			Map<Integer, OptionEntity> listOptions = new HashMap<Integer, OptionEntity>();
			
			for(ProfileEntity profileEntity : _menuFilter) {
				OptionEntity option = this.optionServi.findById(profileEntity.getIdOption());
				listOptions.put(option.getIdOption(), option);
			}
			
			for(ProfileEntity p : _menu) {
				p.setIdModule(listOptions.get(p.getIdOption()).getIdModule());
			}
			
			Map<Integer, List<ProfileEntity>> _groupBy = _menu.stream()
					.collect(Collectors.groupingBy(w -> w.getIdModule()));
			
			ArrayList<MenuEntity> menu = new ArrayList<>();
			
//			for(Map.Entry<Integer, List<ProfileEntity>> l : _groupBy.entrySet()) {
//				ArrayList<ItemMenuEntity> items = new ArrayList<>();
//				for(ProfileEntity e : l.getValue()) {
//					//Debemos encontrar el ícono y el nombre para settearlos en el ItemMenuEntity
//					//Se obtiene la opción con el key y luego el nombre con el valor ya que es un HashMap!!!
//					//Esto es para el nambre y el icon
//					items.add(new ItemMenuEntity(listOptions.get(e.getIdOption()).getName(), 
//							listOptions.get(e.getIdOption()).getIcon()));
//				}
//				//Por esta razon es que colocamos acá el módulo. DEBEMOS RESCATAR LA INFORMACIÓN USANDO LAS OPCIONES
//				//PERO NO LO HACEMOS DIRECTAMENTE CON LA CLAES OPCIONES, para ello, UTILIZAMOS un "Helper" que sería
//				//"ProfileEntity !!" que nos permite relacionar las opciones y los módulos 
//				//POR ESO USAMOS UNA <Integer, List<ProfileEntity>> ya que nos permite tener muchas opciones
//				//pero para sacar el módulo que las contiene, debemos obtener el dato en la posición 0
//				// ---> l.getValue().get(0).getIdModule() 
//				//Así preguntamos módulo por módulo
//				
//				
//				ModuleEntity module = this.moduleServi.findById(l.getValue().get(0).getIdModule());
//				menu.add(new MenuEntity(module.getIdModule(), module.getName(), module.getIcon(), items));
//				
//			}
			LoginInOutEntity response = new LoginInOutEntity();
			response.setUser(_user);
			response.setToken(this.jwt.encodeToken(new UserAuthenticatedEntity(_user.getIdUser(), _user.getName(),
					_user.getLasName(), _user.getUser(), 1/*_menuFilter.get(0).getIdProfile()*/)));
			response.setMenu(menu);
			
			return new RSEntity<LoginInOutEntity>(response, 1);
			
		} catch (RSExceptionEntity e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), e.getCode());
		}
	}
	
}
