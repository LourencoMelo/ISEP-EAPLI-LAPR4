package eapli.base.SPOMSPProtocol;

public class TestRequest extends SPOMSPRequest{

    protected TestRequest(byte[] request) {
        super(request);
    }

    @Override
    public byte[] execute() {
        System.out.println("Communications test request received!");

        byte[] answer = new byte[4];

        //Building answer
        answer[Constants.VERSION_OFFSET] = (byte) Constants.CURRENT_VERSION;
        answer[Constants.CODE_OFFSET] = (byte) Constants.ACK;
        answer[Constants.LENGHT_1_OFFSET] = (byte) 0;
        answer[Constants.LENGHT_2_OFFSET] = (byte) 0;

        return answer;
    }
}
