package dn.rider.json.schema

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode
import com.github.fge.jackson.JacksonUtils
import com.github.fge.jackson.JsonNodeReader
import com.github.fge.jsonschema.core.report.ProcessingReport
import com.github.fge.jsonschema.core.util.AsJson
import com.github.fge.jsonschema.main.JsonSchemaFactory
import com.github.fge.jsonschema.main.JsonValidator
import grails.transaction.Transactional

@Transactional
class JsonSchemaValidationService {

    public static final String RESULTS = "results";
    public static final String VALID = "valid";

    private static final JsonValidator VALIDATOR  = JsonSchemaFactory.byDefault().getValidator()
    private static final JsonNodeReader NODE_READER = new JsonNodeReader();

    def validateSchema(String schema, String dn) {
        //final JsonNode fstabSchema = JsonLoader.fromResource("/NDL_katana_schema.json")
        //schema = fstabSchema.toString()

        final ObjectNode ret = JsonNodeFactory.instance.objectNode()

        final JsonNode schemaNode =  NODE_READER.fromReader(new StringReader(schema))
        final JsonNode dnNode =  NODE_READER.fromReader(new StringReader(dn))

        final ProcessingReport report = VALIDATOR.validateUnchecked(schemaNode, dnNode)

        final boolean success = report.isSuccess()
        ret.put(VALID, success)

        final JsonNode node = ((AsJson) report).asJson()
        ret.put(RESULTS, JacksonUtils.prettyPrint(node))

        return ret
    }
}