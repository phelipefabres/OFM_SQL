package com.net.multiway.ofm.send;

import java.io.DataOutputStream;
import java.io.IOException;

import com.net.multiway.ofm.entities.Parameter;
import com.net.multiway.ofm.model.Header;
import com.net.multiway.ofm.utils.Utils;

/*Classe para envio de dados do código: 0x10000000*/
public class SendParameters {

    private DataOutputStream os;
    private Header header;
    private Parameter data;

    public SendParameters(DataOutputStream out, Parameter data) {
        this.os = out;
        this.header = new Header(0x00000068, 0x10000000, 0x00000030);
        this.data = data;
    }

    public void sender() throws IOException {
        // strpkstr_16
        os.writeBytes(this.header.getSTPKSTR_s16());

        // pklen_4
        os.write(Utils.intToByteArray(this.header.getPKLEN_ui4()));

        // rev_4
        os.write(Utils.intToByteArray(this.header.getREV_ui4()));

        // pktpy_4
        os.write(Utils.intToByteArray(this.header.getPKTPY_ui4()));

        // src_4
        os.write(Utils.intToByteArray(this.header.getSRC_ui4()));

        // dst_4
        os.write(Utils.intToByteArray(this.header.getDST_ui4()));

        // pkid_4
        os.write(Utils.intToByteArray(this.header.getPKID_ui4()));

        // rsvd_4
        os.write(Utils.intToByteArray(this.header.getRSVD1_ui4()));

        // cmcode_4
        os.write(Utils.intToByteArray(this.header.getCMDCODE_ui4()));

        // datalen_4
        os.write(Utils.intToByteArray(this.header.getDATALEN_4()));

        // data
        os.write(this.data.takeData());

        // rsvd_4
        os.write(Utils.intToByteArray(this.header.getRSVD2_ui4()));
    }
}
