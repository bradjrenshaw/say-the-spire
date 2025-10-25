package sayTheSpire.buffers;

import com.megacrit.cardcrawl.screens.leaderboards.LeaderboardEntry;
import com.megacrit.cardcrawl.screens.stats.CharStat;

public class LeaderboardBuffer extends Buffer {

    private int startIndex, endIndex;

    public LeaderboardBuffer() {
        super("leaderboard", "leaderboard");
        this.startIndex = -1;
        this.endIndex = -1;
    }

    public void add(LeaderboardEntry entry) {
        this.context.put("rank", entry.rank);
        this.context.put("name", entry.name);
        if (entry.isTime) {
            this.context.put("date", CharStat.formatHMSM(entry.score));
            this.addLocalized("content.timeEntry");
        } else {
            this.context.put("score", entry.score);
            this.addLocalized("content.scoreEntry");
        }
    }

    public void setIndices(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
}
