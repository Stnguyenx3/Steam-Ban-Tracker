import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {

	Profile p = null;
	String content = null;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		switch (qName) {
		case "steamID64":
			p = new Profile();
			break;
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		switch (qName) {
		case "steamID64":
			p.steamID = content;
			break;
		}

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		content = String.copyValueOf(ch, start, length).trim();
	}

}

class Profile {
	String steamID;

	@Override
	public String toString() {
		return "SteamID = " + steamID;
	}
}
