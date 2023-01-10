import * as React from "react";

interface Props {
  event: {
    id: string;
    title: string;
    start: Date;
    end: Date;
  };
}

const CalendarEvent: React.FC<React.PropsWithChildren<Props>> = ({
  event,
  children,
}) => {
  return (
    <div className="calendar-event">
      <h3>{event.title}</h3>
      <p>
        {event.start.toLocaleDateString()} - {event.end.toLocaleDateString()}
      </p>
      {children}
    </div>
  );
};

export default CalendarEvent;
