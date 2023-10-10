function handleComplete(xhr, status, args) {
    let info;
    if (status == "success") {
        info = "Запрос успешно обработан сервером."
    } else {
        info = "Ошибка во время обработки запроса."
    }
    document.getElementById("ajax-result").textContent = info;
    console.log(args.x, args.y, args.result);
    drowDot(args.x, args.y, args.result);
}