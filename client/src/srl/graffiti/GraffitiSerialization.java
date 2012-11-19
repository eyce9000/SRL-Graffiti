package srl.graffiti;

import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.openawt.Color;
import org.openawt.svg.Style;

import srl.core.serialization.TypeAttributeMixin;
import srl.distributed.ObjectMapperProvider;

public class GraffitiSerialization implements ObjectMapperProvider {
	private static ObjectMapper mapper;
	static{
		mapper = new ObjectMapper();
		mapper.setVisibility(JsonMethod.ALL, Visibility.NONE);
		mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
		mapper.configure(Feature.INDENT_OUTPUT, true);
		mapper.configure(Feature.WRITE_NULL_MAP_VALUES, false);
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT, "@type");
		mapper.getSerializationConfig().addMixInAnnotations(Color.class, TypeAttributeMixin.class);
		mapper.getDeserializationConfig().addMixInAnnotations(Color.class, TypeAttributeMixin.class);
		mapper.getSerializationConfig().addMixInAnnotations(Style.class, TypeAttributeMixin.class);
		mapper.getDeserializationConfig().addMixInAnnotations(Style.class, TypeAttributeMixin.class);
	}

		@Override
		public ObjectMapper getMapper() {
			return mapper;
		}
		
}
