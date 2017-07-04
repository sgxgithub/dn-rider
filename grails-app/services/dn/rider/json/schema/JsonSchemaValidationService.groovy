package dn.rider.json.schema

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode
import com.github.fge.jackson.JsonNodeReader
import com.github.fge.jsonschema.core.report.ProcessingReport
import com.github.fge.jsonschema.main.JsonSchemaFactory
import com.github.fge.jsonschema.main.JsonValidator
import dn.rider.json.schema.constants.ParseError
import grails.io.IOUtils
import grails.transaction.Transactional
import org.grails.web.json.JSONObject


@Transactional
class JsonSchemaValidationService {

    public static final String RESULTS = "results"
    public static final String VALID = "valid"
    public static final String INPUT = "input"
    public static final String INVALID_SCHEMA = "schema-invalid"
    public static final String INPUT2 = "input2"
    public static final String INVALID_DN = "dn-invalid"

    private static final JsonValidator VALIDATOR = JsonSchemaFactory.byDefault().getValidator()
    private static final JsonNodeReader NODE_READER = new JsonNodeReader()

    //function to get schema string from a local file
    def getSchemaText() {
        def schemaStream = this.class.classLoader.getResourceAsStream('NDL_katana_schema.json')
        String schemaText = IOUtils.toString(schemaStream)
        return schemaText
    }

    def validateSchema(String rawSchema, String rawDn) {
        final ObjectNode ret = JsonNodeFactory.instance.objectNode()

        final boolean invalidSchema = fillWithData(ret, INPUT, INVALID_SCHEMA, rawSchema)
        final boolean invalidData = fillWithData(ret, INPUT2, INVALID_DN, rawDn)

        if (invalidSchema || invalidData) {
            log.info "invalide json"
            return ret
        }

        final JsonNode schemaNode = ret.remove(INPUT)
        final JsonNode dnNode = ret.remove(INPUT2)

        final ProcessingReport report = VALIDATOR.validateUnchecked(schemaNode, dnNode)

        final boolean success = report.isSuccess()
        ret.put(VALID, success)

        final JsonNode node = report.asJson()[0]
//        ret.put(RESULTS, JacksonUtils.prettyPrint(node))
        ret.set(RESULTS, node)

        return ret
    }

    /*
    * We have to use that since Java is not smart enough to detect that
    * sometimes, a variable is initialized in all paths.
    *
    * This returns true if the data is invalid.
    */

    private static boolean fillWithData(
            final ObjectNode node, final String onSuccess, final String onFailure, final String raw)
            throws IOException {
        try {
            node.set(onSuccess, NODE_READER.fromReader(new StringReader(raw)))
            return false
        } catch (JsonProcessingException e) {
            node.set(onFailure, ParseError.build(e, raw.contains("\r\n")))
            return true
        }
    }

    /**
     * funtion to parser ObjectNode to JSONObject
     */
    def objectNodeToJSONObject(node) {
        if (node == null) return null
        if (!(node instanceof ObjectNode)) return null

        def json = new JSONObject(node._children)

        json.each { it ->
            def res = objectNodeToJSONObject(it.value)
            if (res != null) json.put(it.key, res)
        }

        return json
    }
}
