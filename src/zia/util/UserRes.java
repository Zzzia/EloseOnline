package zia.util;

import zia.bean.UserBean;

import java.io.IOException;
import java.util.Observable;

public enum UserRes {
    instance;

    private UserData userData = new UserData();

    UserRes() {
        UserBean.DataBean dataBean = UserDataUtil.get();
        if (dataBean != null) {
            userData.change(dataBean);
        }
    }

    public void setUserData(UserBean.DataBean dataBean) {
        userData.change(dataBean);
        try {
            UserDataUtil.write(dataBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isLogin() {
        return !userData.getUsername().isEmpty();
    }

    public UserData getUserData() {
        return userData;
    }

    public class UserData extends Observable {
        /**
         * username : jzljzljl
         * nickname : zia
         * password : jzljzljzl
         * createDate : May 1, 2018
         */

        private String username;
        private String nickname;
        private String password;
        private String createDate;

        UserData() {
            username = "";
            nickname = "";
            password = "";
            createDate = "";
        }

        public void change(UserBean.DataBean dataBean) {
            this.setCreateDate(dataBean.getCreateDate());
            this.setNickname(dataBean.getNickname());
            this.setPassword(dataBean.getPassword());
            this.setUsername(dataBean.getUsername());
            setChanged();
            this.notifyObservers(this);
        }

        public String getUsername() {
            return username;
        }

        void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPassword() {
            return password;
        }

        void setPassword(String password) {
            this.password = password;
        }

        public String getCreateDate() {
            return createDate;
        }

        void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "username='" + username + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", password='" + password + '\'' +
                    ", createDate='" + createDate + '\'' +
                    '}';
        }
    }
}
