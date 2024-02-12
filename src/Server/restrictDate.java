package Server;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.Month;

public class restrictDate extends DateCell {


    public static DatePicker createRestrictedDatePicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.getMonth() != Month.OCTOBER || date.getYear() != 2021) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // Disable dates outside October 2021
                }
            }
        });
        return datePicker;
    }
}
