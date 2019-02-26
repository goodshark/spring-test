package test.objects;


public class CustomPara {

    private int seq;
    private String desc;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "seq: " + seq + ", desc: " + desc;
    }

}
