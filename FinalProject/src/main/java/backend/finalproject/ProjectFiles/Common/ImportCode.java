package backend.finalproject.ProjectFiles.Common;

import frontend.finalproject.Model.Common.ImportCodeModel;

import java.util.ArrayList;
import java.util.List;

public class ImportCode {
    String From;
    List<String> Import;

    public ImportCode(String from, List<String> anImport) {
        From = from;
        Import = anImport;
    }

    public ImportCode(ImportCodeModel i) {
        From = i.getFrom();
        Import = new ArrayList<>();
        Import.addAll(i.getImport());
    }

    public String getFrom() {
        return From;
    }

    public List<String> getImport() {
        return Import;
    }
}