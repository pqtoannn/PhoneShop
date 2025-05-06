
package DTO;


public class DTO_Date {
    //Attributes:
    private int yyyy;
    private int mm;
    private int dd;
    private int hh;
    private int mi;
    private int ss;

    //Constructors:
    public DTO_Date() {
    }

    public DTO_Date(int yyyy, int mm, int dd, int hh, int mi, int ss) {
        this.yyyy = yyyy;
        this.mm = mm;
        this.dd = dd;
        this.hh = hh;
        this.mi = mi;
        this.ss = ss;
    }

    //Getters:
    public int getYyyy() {
        return yyyy;
    }

    public int getMm() {
        return mm;
    }

    public int getDd() {
        return dd;
    }

    //Setters:
    public void setYYYY(int yyyy) {
        this.yyyy = yyyy;
    }

    public void setMm(int mm) {
        this.mm = mm;
    }

    public void setDd(int dd) {
        this.dd = dd;
    }

    public int getHh() {
        return hh;
    }

    public void setHh(int hh) {
        this.hh = hh;
    }

    public int getMi() {
        return mi;
    }

    public void setMi(int mi) {
        this.mi = mi;
    }

    public int getSs() {
        return ss;
    }

    public void setSs(int ss) {
        this.ss = ss;
    }
    
    //toString:
    @Override
    public String toString() {
        String m = getMm() + "";
        String d = getDd() + "";
        
        switch(m.length()) {
            case 1:
                m = "0" + m;
                break;
            default:
        }
        
        switch(d.length()) {
            case 1:
                d = "0" + d;
                break;
            default:
        }
        
        String h  = getHh() + "";
        String mi = getMi() + "";
        String s = getSs() + "";
        
        switch(h.length()) {
            case 1:
                h = "0" + h;
                break;
            default:
        }
        
        switch(mi.length()) {
            case 1:
                mi = "0" + mi;
                break;
            default:
        }
        
        switch(s.length()) {
            case 1:
                s = "0" + s;
                break;
            default:
        }
        
        return getYyyy() + "-" + m + "-" + d + " " + h + ":" + mi + ":" + s;
    }
    
    public String format() {
        String m = getMm() + "";
        String d = getDd() + "";
        
        switch(m.length()) {
            case 1:
                m = "0" + m;
                break;
            default:
        }
        
        switch(d.length()) {
            case 1:
                d = "0" + d;
                break;
            default:
        }
        
        String h  = getHh() + "";
        String mi = getMi() + "";
        String s = getSs() + "";
        
        switch(h.length()) {
            case 1:
                h = "0" + h;
                break;
            default:
        }
        
        switch(mi.length()) {
            case 1:
                mi = "0" + mi;
                break;
            default:
        }
        
        switch(s.length()) {
            case 1:
                s = "0" + s;
                break;
            default:
        }
        
        return d + "/" + m + "/" + getYyyy() + " " + h + ":" + mi + ":" + s;
    }

    public boolean isEqual(DTO_Date otherDate) {
        return (this.yyyy == otherDate.yyyy) &&
               (this.mm == otherDate.mm) &&
               (this.dd == otherDate.dd);
    }

    public boolean isBefore(DTO_Date otherDate) {
        if (this.yyyy < otherDate.yyyy) {
            return true;
        } else if (this.yyyy == otherDate.yyyy) {
            if (this.mm < otherDate.mm) {
                return true;
            } else if (this.mm == otherDate.mm) {
                return this.dd < otherDate.dd;
            }
        }
        return false;
    }

    public boolean isAfter(DTO_Date otherDate) {
        if (this.yyyy > otherDate.yyyy) {
            return true;
        } else if (this.yyyy == otherDate.yyyy) {
            if (this.mm > otherDate.mm) {
                return true;
            } else if (this.mm == otherDate.mm) {
                return this.dd > otherDate.dd;
            }
        }
        return false;
    }

    public int compareTo(DTO_Date otherDate) {
        if (this.yyyy != otherDate.yyyy) {
            return Integer.compare(this.yyyy, otherDate.yyyy);
        } else if (this.mm != otherDate.mm) {
            return Integer.compare(this.mm, otherDate.mm);
        } else if (this.dd != otherDate.dd) {
            return Integer.compare(this.dd, otherDate.dd);
        } else if (this.hh != otherDate.hh) {
            return Integer.compare(this.hh, otherDate.hh);
        } else if (this.mi != otherDate.mi) {
            return Integer.compare(this.mi, otherDate.mi);
        } else {
            return Integer.compare(this.ss, otherDate.ss);
        }
    }

    
    
    
}
