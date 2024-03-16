package uni.fmi.eventify.entity;

public class Score {
    private int id;
    private int score;
    private String code;
    private int codePercent;
    private boolean isUsed;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCodePercent() {
        return codePercent;
    }

    public void setCodePercent(int codePercent) {
        this.codePercent = codePercent;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
