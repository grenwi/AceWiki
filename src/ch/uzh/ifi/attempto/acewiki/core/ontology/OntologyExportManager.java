package ch.uzh.ifi.attempto.acewiki.core.ontology;

import java.util.ArrayList;
import java.util.List;

public class OntologyExportManager {
	
	private List<OntologyExporter> exporters = new ArrayList<OntologyExporter>();
	
	public OntologyExportManager() {
	}
	
	public void addExporter(OntologyExporter exporter) {
		if (exporter.isApplicable()) {
			exporters.add(exporter);
		} else {
			System.err.println("Ignoring non-applicable exporter: " + exporter.getText());
		}
	}
	
	public void removeExporter(OntologyExporter exporter) {
		exporters.remove(exporter);
	}
	
	public void removeAllExporters() {
		exporters.clear();
	}
	
	public List<OntologyExporter> getExporters() {
		return new ArrayList<OntologyExporter>(exporters);
	}

}
