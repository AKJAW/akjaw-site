LANGUAGE = {
    "pl-button": "pl",
    "en-button": "en"
}

document.addEventListener("DOMContentLoaded", function(event) {
    setLaungageButtonsListener()
    console.log("asd")
});

function setLaungageButtonsListener(){
    addEventListenerQuery(".language-button", "click", onLanguageButtonClick)
}

function addEventListenerQuery(query, event, fn) {
    var list = document.querySelectorAll(query);
    for (var i = 0, len = list.length; i < len; i++) {
        list[i].addEventListener(event, fn, false);
    }
}

function onLanguageButtonClick(event){
    const activatedLanguage = toggleButtonActivation(event.target)
    toggleLanguageText(activatedLanguage)
    //TODO pokaz i ukryj tekst tak jak toggleButtonActivation
}


function toggleButtonActivation(button) {
    button.addClass("active")

    const sibling = button.getShownSibling()
    sibling.removeClass("active")

    return LANGUAGE[button.id]
}

function toggleLanguageText(activatedLanguage){
    textNodes = document.querySelectorAll("." + activatedLanguage)

    for (var i = 0, len = textNodes.length; i < len; i++) {
        console.log(textNodes[i])
        debugger;
        textNodes[i].toggleLanguage()
    }
}

HTMLElement.prototype.toggleLanguage = function(){
    this.removeClass("none")

    const sibling = this.getShownSibling()
    sibling.addClass("none")
}

HTMLElement.prototype.removeClass = function(className){
    this.classList.remove(className)
}

HTMLElement.prototype.addClass = function(className){
    this.classList.add(className)
}

HTMLElement.prototype.getShownSibling = function(){//That is shown
    if(this.previousSibling && !this.previousSibling.hasClass("none")){
        return this.previousSibling
    } else if(this.nextSibling && !this.nextSibling.hasClass("none")){
        return this.nextSibling
    }
}

HTMLElement.prototype.hasClass = function(className){
    return this.classList.contains(className)
}
