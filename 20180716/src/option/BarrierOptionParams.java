package option;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author liangcy
 */
public class BarrierOptionParams implements Serializable {
    private double barrierPrice;
    private String barrierType;
    private String barrierDirection;
    private String payoffType = BaseOption.PAYOFF_TYPE_HIT;
    //下面是双障碍期权参数

    private double upperBarrierPrice;
    private double lowerBarrierPrice;
    private double upperCurve = 0.0;
    private double lowerCurve = 0.0;

    private int maxIterationTimes = 50;
    private double tolerance = 1e-8;

    public int getMaxIterationTimes() {
        return maxIterationTimes;
    }

    public void setMaxIterationTimes(int maxIterationTimes) {
        this.maxIterationTimes = maxIterationTimes;
    }

    public double getTolerance() {
        return tolerance;
    }

    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    public String getPayoffType() {
        return payoffType;
    }

    public void setPayoffType(String payoffType) {
        this.payoffType = payoffType;
    }

    public String getBarrierDirection() {
        return barrierDirection;
    }

    public void setBarrierDirection(String barrierDirection) {
        this.barrierDirection = barrierDirection;
    }

    public double getBarrierPrice() {
        return barrierPrice;
    }

    public void setBarrierPrice(double barrierPrice) {
        this.barrierPrice = barrierPrice;
    }

    public String getBarrierType() {
        return barrierType;
    }

    public void setBarrierType(String barrierType) {
        this.barrierType = barrierType;
    }

    public double getUpperBarrierPrice() {
        return upperBarrierPrice;
    }

    public void setUpperBarrierPrice(double upperBarrierPrice) {
        this.upperBarrierPrice = upperBarrierPrice;
    }

    public double getLowerBarrierPrice() {
        return lowerBarrierPrice;
    }

    public void setLowerBarrierPrice(double lowerBarrierPrice) {
        this.lowerBarrierPrice = lowerBarrierPrice;
    }

    public double getUpperCurve() {
        return upperCurve;
    }

    public void setUpperCurve(double upperCurve) {
        this.upperCurve = upperCurve;
    }

    public double getLowerCurve() {
        return lowerCurve;
    }

    public void setLowerCurve(double lowerCurve) {
        this.lowerCurve = lowerCurve;
    }

    void swapInOut() {
        if (isIn()) {
            setBarrierType(BaseOption.BARRIER_TYPE_OUT);
        } else {
            setBarrierType(BaseOption.BARRIER_TYPE_IN);
        }
    }

    boolean isUp() {
        return BaseOption.BARRIER_DIRECTION_UP.equals(barrierDirection);
    }

    boolean isIn() {
        return BaseOption.BARRIER_TYPE_IN.equals(barrierType);
    }

    boolean isPayAtHit() {
        return BaseOption.PAYOFF_TYPE_HIT.equals(payoffType);
    }

    /**
     * @param spotPrice 标的资产价格
     * @return 是否触碰了障碍值
     */
    boolean isTouchSingleBarrier(double spotPrice) {
        return isUp() ? (spotPrice > barrierPrice) : (spotPrice < barrierPrice);
    }

    /**
     *
     * @param spotPrice 标的资产价格
     * @return 是否触碰了双障碍的任一障碍值
     */
    boolean isTouchDoubleBarrier(double spotPrice) {
        return spotPrice > upperBarrierPrice || spotPrice < lowerBarrierPrice;
    }



    String singleBarrierToString() {
        return "BarrierOptionParams{" +
                "barrierPrice=" + barrierPrice +
                ", barrierType='" + barrierType + '\'' +
                ", barrierDirection='" + barrierDirection + '\'' +
                '}';
    }

    String doubleBarrierToString() {
        return "BarrierOptionParams{" +
                "barrierType='" + barrierType + '\'' +
                ", upperBarrierPrice=" + upperBarrierPrice +
                ", lowerBarrierPrice=" + lowerBarrierPrice +
                ", upperCurve=" + upperCurve +
                ", lowerCurve=" + lowerCurve +
                '}';
    }

    boolean isValidSingleBarrierParams() {
        return barrierPrice > 0 &&
                (BaseOption.BARRIER_TYPE_IN.equals(barrierType) || BaseOption.BARRIER_TYPE_OUT.equals(barrierType)) &&
                (BaseOption.BARRIER_DIRECTION_UP.equals(barrierDirection) || BaseOption.BARRIER_DIRECTION_DOWN.equals(barrierDirection)) &&
                (BaseOption.PAYOFF_TYPE_HIT.equals(payoffType) || BaseOption.PAYOFF_TYPE_EXPIRE.equals(payoffType));
    }

    boolean isValidDoubleBarrierParams(double timeRemaining) {
        return lowerBarrierPrice > 0 &&
                upperBarrierPrice > lowerBarrierPrice &&
                upperBarrierPrice * Math.exp(upperCurve * timeRemaining) > lowerBarrierPrice * Math.exp(lowerCurve * timeRemaining) &&
                (BaseOption.BARRIER_TYPE_IN.equals(barrierType) || BaseOption.BARRIER_TYPE_OUT.equals(barrierType)) &&
                (BaseOption.PAYOFF_TYPE_HIT.equals(payoffType) || BaseOption.PAYOFF_TYPE_EXPIRE.equals(payoffType));
    }
}
