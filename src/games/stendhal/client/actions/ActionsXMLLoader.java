package games.stendhal.client.actions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ActionsXMLLoader extends DefaultHandler{
	
	private Map<String, BaseAction> map;
	private Map<String, String> parameters, staticParameters;
	
	private String name;
	
	private int minParams, maxParams;
	
	private boolean parametersTag = false;
	private boolean staticParametersTag = false;
	private boolean remainderRequired;
	private boolean remainderNonEmpty;
	
	public Map<String, BaseAction> load(final URI uri) throws SAXException {
		map = new LinkedHashMap<String, BaseAction>();
		// Use the default (non-validating) parser
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			// Parse the input
			final SAXParser saxParser = factory.newSAXParser();

			final InputStream is = ActionsXMLLoader.class.getResourceAsStream(uri.getPath());

			if (is == null) {
				throw new FileNotFoundException("cannot find resource '" + uri
						+ "' in classpath");
			}
			try {
				saxParser.parse(is, this);
			} finally {
				is.close();
			}
		} catch (final ParserConfigurationException t) {
			//LOGGER.error(t);
		} catch (final IOException e) {
			//sLOGGER.error(e);
			throw new SAXException(e);
		}

		return map;
		
	}
	
	@Override
	public void startDocument() {
		// do nothing
	}

	@Override
	public void endDocument() {
		// do nothing
	}
	
	@Override
	public void startElement(final String namespaceURI, final String lName, final String qName,
			final Attributes attrs) {
		if (qName.equals("action")) {
			name = attrs.getValue("name");
			parameters = new LinkedHashMap<String, String>();
			staticParameters = new LinkedHashMap<String, String>();
			remainderNonEmpty = false;
			remainderRequired = false;
		} else if (qName.equals("min_params")) {
			minParams = Integer.parseInt(attrs.getValue("value"));
		} else if (qName.equals("max_params")) {
			maxParams = Integer.parseInt(attrs.getValue("value"));
		} else if (qName.equals("remainder_required")) {
			remainderRequired = true;
		} else if (qName.equals("remainder_non_empty")) {
			remainderNonEmpty = true;
		} else if (qName.equals("static_parameters")) {
			staticParametersTag = true;
		} else if (qName.equals("parameters")) {
			parametersTag = true;
		} else if (staticParametersTag) {
			staticParameters.put(qName, attrs.getValue("value"));
		} else if (parametersTag) {
			parameters.put(qName, attrs.getValue("value"));
		} 
	}

	@Override
	public void endElement(final String namespaceURI, final String sName, final String qName) {
		if (qName.equals("action")) {
			final BaseAction action = new BaseAction(minParams, maxParams, parameters, staticParameters);
			action.setRemainderNonEmpty(remainderNonEmpty);
			action.setRemainderRequired(remainderRequired);

			map.put(name, action);
		} else if (qName.equals("parameters")) {
			parametersTag = false;
		} else if (qName.equals("static_parameters")) {
			staticParametersTag = false;
		}
	}
	
}
