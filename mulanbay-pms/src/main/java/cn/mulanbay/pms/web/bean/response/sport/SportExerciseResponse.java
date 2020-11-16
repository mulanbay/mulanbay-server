package cn.mulanbay.pms.web.bean.response.sport;

import cn.mulanbay.pms.persistent.domain.SportExercise;
import cn.mulanbay.pms.persistent.domain.SportMilestone;

import java.util.List;

public class SportExerciseResponse extends SportExercise {

    private long sportMilestones;

    private List<SportMilestone> sportMilestoneList;

    public long getSportMilestones() {
        return sportMilestones;
    }

    public void setSportMilestones(long sportMilestones) {
        this.sportMilestones = sportMilestones;
    }

    public List<SportMilestone> getSportMilestoneList() {
        return sportMilestoneList;
    }

    public void setSportMilestoneList(List<SportMilestone> sportMilestoneList) {
        this.sportMilestoneList = sportMilestoneList;
    }
}
