
package DTO;


public class DTO_BusinessReport {
    private int month;
    private double revenue, expense, profit;

    public DTO_BusinessReport() {
    }

    public DTO_BusinessReport(int month, double revenue, double expense) {
        this.month = month;
        this.revenue = revenue;
        this.expense = expense;
        this.profit = this.revenue - this.expense;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
    
}
