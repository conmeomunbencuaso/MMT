/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package totoro;

import java.io.Serializable;

/**
 *
 * @author Totoro
 */
public class Package implements Serializable {

    private Header header;
    private Object data;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Package(Header header, Object data) {
        this.header = header;
        this.data = data;
    }

    public Package() {

    }

    public boolean equal(Package pag) {
        return this.data.equals(pag.data) && this.header == pag.header;
    }
}
