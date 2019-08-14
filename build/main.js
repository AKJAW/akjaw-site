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

    var len
    for (var i = 0, len = textNodes.length; i < len; i++) {
        console.log(textNodes[i])
        textNodes[i].toggleLanguage()
    }
}

HTMLElement.prototype.toggleLanguage = function(){
    this.removeClass("none")

    var sibling = this.getShownLanguageSibling()
    sibling.addClass("none")
}

HTMLElement.prototype.removeClass = function(className){
    this.classList.remove(className)
}

HTMLElement.prototype.addClass = function(className){
    this.classList.add(className)
}

HTMLElement.prototype.getShownSibling = function(){
    if(this.previousSibling && !this.previousSibling.hasClass("none")){
        return this.previousSibling
    } else if(this.nextSibling && !this.nextSibling.hasClass("none")){
        return this.nextSibling
    }
}


HTMLElement.prototype.getShownLanguageSibling = function(){
    if(this.previousSibling &&
        this.previousSibling.isLanguageTag() &&
        !this.previousSibling.hasClass("none") ){

        return this.previousSibling
    } else if(this.nextSibling &&
        this.nextSibling.isLanguageTag() &&
        !this.nextSibling.hasClass("none")){

        return this.nextSibling
    }
}

HTMLElement.prototype.isLanguageTag = function(){
    return this.hasClass("pl") || this.hasClass("en")
}

HTMLElement.prototype.hasClass = function(className){
    return this.classList.contains(className)
}
