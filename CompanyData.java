package stock;

public class CompanyData {
    public String id;
    public String ts;
    public String srr;
    public String sdd;

    public CompanyData(String id,String ts, String srr, String sdd) {
        this.id=id;
        this.ts = ts;
        this.srr = srr;
        this.sdd = sdd;
    }

    public String getId(){
        return id;
    }
    public String getTs() {
        return ts;
    }

    public String getSrr() {
        return srr;
    }

    public String getSdd() {
        return sdd;
    }
}
