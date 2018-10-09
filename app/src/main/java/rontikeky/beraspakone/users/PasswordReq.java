package rontikeky.beraspakone.users;

/**
 * Created by Acer on 2/25/2018.
 */

public class PasswordReq {

    public String action;
    public Integer id_user;
    public String old_pass;
    public String new_pass;

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public Integer getId_user() {
            return id_user;
        }

        public void setId_user(Integer id_user) {
            this.id_user = id_user;
        }

        public String getOld_pass() {
            return old_pass;
        }

        public void setOld_pass(String old_pass) {
            this.old_pass = old_pass;
        }

        public String getNew_pass() {
            return new_pass;
        }

        public void setNew_pass(String new_pass) {
            this.new_pass = new_pass;
        }

    public PasswordReq (String action, Integer idUser, String oldPass, String newPass) {
            this.action = "edit_password";
            this.id_user = idUser;
            this.old_pass = oldPass;
            this.new_pass = newPass;
    }
}
