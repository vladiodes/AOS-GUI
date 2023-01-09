package frontend.finalproject.Model.Common;

import java.util.ArrayList;
import java.util.List;

public class ImportCodeModel {
    private String From;
    private List<String> Import;

    public ImportCodeModel() {
        Import = new ArrayList<>();
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public void addImportValue(String val) {
        Import.add(val);
    }

    public List<String> getImport() {
        return Import;
    }
}
