package sayTheSpire.buffers;

public class LeaderboardBuffer extends Buffer {

    private int startIndex, endIndex;

    public LeaderboardBuffer() {
        super("leaderboard", "leaderboard");
        this.startIndex = -1;
        this.endIndex = -1;
    }

    public void setIndices(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
}
