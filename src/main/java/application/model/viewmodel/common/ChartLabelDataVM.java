package application.model.viewmodel.common;

public class ChartLabelDataVM {
    private String label;
    private long data;

    public ChartLabelDataVM(String label, long data) {
        this.label = label;
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }
}
