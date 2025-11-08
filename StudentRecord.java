package studentlogger;

public class StudentRecord {
    private final String studentName;
    private final String regNo;
    private final int score;

    public StudentRecord(String studentName, String regNo, int score) {
        this.studentName = studentName;
        this.regNo = regNo;
        this.score = score;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getRegNo() {
        return regNo;
    }

    public int getScore() {
        return score;
    }

    public String toCsvFormat() {
        return studentName + "," + regNo + "," + score;
    }
}
