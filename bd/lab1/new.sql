SELECT Н_УЧЕНИКИ.ГРУППА, Н_УЧЕНИКИ.ИД, Н_ЛЮДИ.ФАМИЛИЯ, Н_ЛЮДИ.ИМЯ FROM Н_УЧЕНИКИ INNER JOIN Н_ФОРМЫ_ОБУЧЕНИЯ ON Н_УЧЕНИКИ.ВИД_ОБУЧ_ИД = Н_ФОРМЫ_ОБУЧЕНИЯ.ИД INNER JOIN Н_ЛЮДИ ON Н_УЧЕНИКИ.ЧЛВК_ИД = Н_ЛЮДИ.ИД WHERE Н_ФОРМЫ_ОБУЧЕНИЯ.НАИМЕНОВАНИЕ = 'Очно-заочная(вечерняя)' AND Н_УЧЕНИКИ.ПРИЗНАК = 'отчисл' AND Н_УЧЕНИКИ.КОНЕЦ < CAST('01-09-2012' as date);