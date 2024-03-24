package com.mobileapp.wowapp.utils;

import com.mobileapp.wowapp.driver.model.Slot;
import com.mobileapp.wowapp.model.Appointment;
import com.mobileapp.wowapp.serviceprovider.model.ServiceProvider;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SlotManager
{
    public interface SlotUpdateListener {
        void onSlotsUpdated(int availableSlots,List<Slot>slots);
    }

    public void getslots(ServiceProvider serviceProvider, List<Appointment> appointments, String date, SlotUpdateListener listener) {
        List<Slot> slots = new ArrayList<>();

        Set<LocalDateTime> bookedSlots = new HashSet<>();
        for (Appointment appointment : appointments)
        {
            LocalDateTime localDateTime = LocalDateTime.parse(appointment.getAppointment_time(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            bookedSlots.add(localDateTime);
        }

        String startTime=date+" "+serviceProvider.getBusinessStartTime();
        String endTime=date+" "+serviceProvider.getBusinessEndTime();
        String startBreak=date+" "+serviceProvider.getBreakStartTime();
        String endBreak=date+" "+serviceProvider.getBreakEndTime();

        LocalDateTime currentSlotStart = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime businessEndTime = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        DateTimeFormatter twentyFourHourformatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        Duration slotDuration = Duration.ofMinutes(30);
        int availableSlots = 0;

        while (currentSlotStart.plus(slotDuration).isBefore(businessEndTime))
        {
            LocalDateTime currentSlotEnd = currentSlotStart.plus(slotDuration);

            boolean isBooked = bookedSlots.contains(currentSlotStart) ||
                    isWithinBreakTime(startBreak,endBreak, currentSlotStart);


            Slot slot = new Slot(currentSlotStart.format(formatter), currentSlotEnd.format(formatter), isBooked);
            slot.setAppointmentDate(date+" "+currentSlotStart.format(twentyFourHourformatter));
            slots.add(slot);

            if (!isBooked) {
                availableSlots++;
            }

            // Move to next slot
            currentSlotStart = currentSlotStart.plus(slotDuration);
        }

        // Update UI
        if (listener != null) {
            listener.onSlotsUpdated(availableSlots,slots);
        }

    }


    private boolean isWithinBreakTime(String startBreak,String endBreak, LocalDateTime dateTime)
    {
        LocalDateTime breakStartTime = LocalDateTime.parse(startBreak, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime breakEndTime = LocalDateTime.parse(endBreak, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return !dateTime.toLocalTime().isBefore(breakStartTime.toLocalTime())
                && dateTime.toLocalTime().isBefore(breakEndTime.toLocalTime());
    }
}
