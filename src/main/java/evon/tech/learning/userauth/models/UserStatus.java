package evon.api.userauth.models;

public enum UserStatus {
    ACTIVE(1), INACTIVE(2);

    private int value;
    private UserStatus(int value){
        this.value=value;
    }
}
