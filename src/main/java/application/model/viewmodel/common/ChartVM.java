package application.model.viewmodel.common;

import java.util.List;

public class ChartVM {

    private List<ChartLabelDataVM> labelDataList;

    public List<ChartLabelDataVM> getLabelDataList() {
        return labelDataList;
    }

    public void setLabelDataList(List<ChartLabelDataVM> labelDataList) {
        this.labelDataList = labelDataList;
    }
}
