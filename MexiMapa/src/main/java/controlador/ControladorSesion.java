/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.Serializable;
import modelo.Usuario;
import modelo.UsuarioDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Mensajes;
import modelo.Rol;

/**
 *
 * @author jonathan
 */
@ManagedBean
@SessionScoped
public class ControladorSesion implements Serializable{

    private String correo;
    private String contrasenia;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String login(){
        UsuarioDAO udb = new UsuarioDAO();
        Usuario user = udb.buscaPorCorreoContrasenia(correo, contrasenia);
        FacesContext context = FacesContext.getCurrentInstance();
        if(user !=null){
            UserLogged u = new UserLogged(user.getNombre(),user.getCorreo(),user.getRol());
            
            if("ADMINISTRADOR".equals(user.getRol())){
                 context.getExternalContext().getSessionMap().put("ADMINISTRADOR", u);
                return "/user/administrador?faces-redirect=true";
            }else if("INFORMADOR".equals(user.getRol())){
                 context.getExternalContext().getSessionMap().put("INFORMADOR", u);
                return "/user/informador?faces-redirect=true";
            }else{
                 context.getExternalContext().getSessionMap().put("COMENTARISTA", u);
                return "/user/comentarista?faces-redirect=true";
            }
        }
        Mensajes.error("NO hay usuarios con este correo"+this.correo);
        return "";
    }
    
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index?faces-redirect=true";
    }
    public class UserLogged implements Serializable{
        private String nombre;
        private String correo;
        private String rol;

        public UserLogged(String nombre, String correo, String rol) {
            this.nombre = nombre;
            this.correo = correo;
            this.rol = rol;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getRol() {
            return rol;
        }

        public void setRol(String rol) {
            this.rol = rol;
        }
        
        
    }

}
