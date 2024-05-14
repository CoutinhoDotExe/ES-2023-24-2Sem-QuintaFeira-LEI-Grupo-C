package pt.iscte_iul.ista.ES_2324_LEI_GC;

public class TimeSlot {
    String time;

    public TimeSlot(int slot) {
        switch (slot) {
            case 0:
                time = "8:00:00";
                break;
            case 1:
                time = "8:30:00";
                break;
            case 2:
                time = "9:00:00";
                break;
            case 3:
                time = "9:30:00";
                break;
            case 4:
                time = "10:00:00";
                break;
            case 5:
                time = "10:30:00";
                break;
            case 6:
                time = "11:00:00";
                break;
            case 7:
                time = "11:30:00";
                break;
            case 8:
                time = "12:00:00";
                break;
            case 9:
                time = "12:30:00";
                break;
            case 10:
                time = "13:00:00";
                break;
            case 11:
                time = "13:30:00";
                break;
            case 12:
                time = "14:00:00";
                break;
            case 13:
                time = "14:30:00";
                break;
            case 14:
                time = "15:00:00";
                break;
            case 15:
                time = "15:30:00";
                break;
            case 16:
                time = "16:00:00";
                break;
            case 17:
                time = "16:30:00";
                break;
            case 18:
                time = "17:00:00";
                break;
            case 19:
                time = "17:30:00";
                break;
            case 20:
                time = "18:00:00";
                break;
            case 21:
                time = "18:30:00";
                break;
            case 22:
                time = "19:00:00";
                break;
            case 23:
                time = "19:30:00";
                break;
            case 24:
                time = "20:00:00";
                break;
            case 25:
                time = "20:30:00";
                break;
            case 26:
                time = "21:00:00";
                break;
            case 27:
                time = "21:30:00";
                break;
            case 28:
                time = "22:00:00";
                break;
            case 29:
                time = "22:30:00";
                break;
            default:
                throw new IllegalArgumentException("Invalid slot number: " + slot);
        }
    }

    public String getSlot() {
        return time;
    }
}
