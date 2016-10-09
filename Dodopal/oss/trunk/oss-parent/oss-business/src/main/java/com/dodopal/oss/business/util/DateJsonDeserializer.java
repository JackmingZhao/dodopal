package com.dodopal.oss.business.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import com.dodopal.common.util.DDPStringUtil;

public class DateJsonDeserializer extends JsonDeserializer<Date> {
	public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  

	  @Override
      public Date  deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		  try {  
			    if (!DDPStringUtil.isPopulated(jsonParser.getText())) return null;
	            return format.parse(jsonParser.getText());  
	        } catch (ParseException e) {  
	            throw new RuntimeException(e);  
	        }  
      }
}
