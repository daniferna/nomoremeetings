import * as React from "react";
import CalendarEvent from "./CalendarEvent";

interface Event {
  id: string;
  title: string;
  start: Date;
  end: Date;
}

const Calendar: React.FC = () => {
  const [events, setEvents] = React.useState<Event[]>([]);

  React.useEffect(() => {
    const fetchEvents = async () => {
      const response = await fetch("/api/events");
      const data = await response.json();
      setEvents(data);
    };
    fetchEvents();
  }, []);

  return (
    <div className="calendar">
      {events.map((event) => (
        <CalendarEvent key={event.id} event={event} />
      ))}
    </div>
  );
};

export default Calendar;
