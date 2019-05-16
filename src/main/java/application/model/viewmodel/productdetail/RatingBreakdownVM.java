package application.model.viewmodel.productdetail;

public class RatingBreakdownVM {
    private int numOfRatingEachStar;
    private int percentOfRatingEachStar;

    public int getNumOfRatingEachStar() {
        return numOfRatingEachStar;
    }

    public void setNumOfRatingEachStar(int numOfRatingEachStar) {
        this.numOfRatingEachStar = numOfRatingEachStar;
    }

    public int getPercentOfRatingEachStar() {
        return percentOfRatingEachStar;
    }

    public void setPercentOfRatingEachStar(int percentOfRatingEachStar) {
        this.percentOfRatingEachStar = percentOfRatingEachStar;
    }
}
