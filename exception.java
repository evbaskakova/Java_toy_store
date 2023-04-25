public class exception extends Exception{
    private String info;
    public exception(int error, String message) {
        super(message);
        this.info = String.format("Код ошибки %d", error);
        if (((error >> 0) & 1) == 1) {
            this.info += "\r\nНе задано имя игрушки";
        }
        if (((error >> 1) & 1) == 1) {
            this.info += "\r\nНе задано/не корректно задано количество игрушек";
        }
        if (((error >> 2) & 1) == 1) {
            this.info += "\r\nНе задан/не корректно задан шанс розыгрыша игрушки";
        }
    }

    public String getInfo() {return info;}
    
}