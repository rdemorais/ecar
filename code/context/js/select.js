//FUN??O PARA ORDENAR COMBO

//Inclu?mos a fun??o removeAcento, pois pela ordena??o crescente do JavaScript
// as palavras que iniciam com acento estariam ap?s a letra Z

    <!--

        // sort function - ascending (case-insensitive)
        function sortFuncAsc(record1, record2) {
            var value1 = removeAcento(record1.optText.toLowerCase());
            var value2 = removeAcento(record2.optText.toLowerCase());
            if (value1 > value2) return(1);
            if (value1 < value2) return(-1);
            return(0);
        }

        // sort function - descending (case-insensitive)
        function sortFuncDesc(record1, record2) {
            var value1 = removeAcento(record1.optText.toLowerCase());
            var value2 = removeAcento(record2.optText.toLowerCase());
            if (value1 > value2) return(-1);
            if (value1 < value2) return(1);
            return(0);
        }

        function sortSelect(selectToSort, ascendingOrder) {
            if (arguments.length == 1) ascendingOrder = true;    // default to ascending sort

            // copy options into an array
            var myOptions = [];
            for (var loop=0; loop<selectToSort.options.length; loop++) {
                myOptions[loop] = { optText:selectToSort.options[loop].text, optValue:selectToSort.options[loop].value };
            }

            // sort array
            if (ascendingOrder) {
                myOptions.sort(sortFuncAsc);
            } else {
                myOptions.sort(sortFuncDesc);
            }

            // copy sorted options from array back to select box
            selectToSort.options.length = 0;
            for (var loop=0; loop<myOptions.length; loop++) {
                var optObj = document.createElement('option');
                optObj.text = myOptions[loop].optText;
                optObj.value = myOptions[loop].optValue;
                selectToSort.options.add(optObj);
            }
        }
    //-->
