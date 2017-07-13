/*
 * Function added to set the cursor position at a given offset in a text area
 * Found at: http://stackoverflow.com/questions/499126/jquery-set-cursor-position-in-text-area
 */

$.fn.setCursorPosition = function (pos) {
    if ($(this).get(0).setSelectionRange) {
        $(this).get(0).setSelectionRange(pos, pos);
    } else if ($(this).get(0).createTextRange) {
        let range = $(this).get(0).createTextRange();
        range.collapse(true);
        range.moveEnd('character', pos);
        range.moveStart('character', pos);
        range.select();
    }
};