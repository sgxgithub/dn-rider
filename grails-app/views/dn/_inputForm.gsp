<form id="process" method="post">
    <div id="left" class="content">
        <div class="horiz">
            <label for="input">Schema:</label>
            <span class="error starthidden" id="input-invalid">Invalid JSON: parse error, <a id="input-link" href="#"> </a></span>
        </div>
        <textarea name="input" rows="20" cols="20" class="half" id="input"> </textarea>
        <div class="horiz">
            <label for="input2">DN:</label>
            <span class="error starthidden" id="input2-invalid">Invalid JSON:
            parse error, <a id="input2-link" href="#"> </a></span>
        </div>
        <textarea name="input2" rows="20" cols="20" class="half" id="input2"> </textarea>
        <div class="horiz">
            <input type="submit" value="Check Syntax">
            <span>(<a id="load" href="#">load sample data</a>)</span>
        </div>
    </div>
</form>
