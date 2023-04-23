class Calendar {
    constructor(events) {
        this.events = events.map((event) => {
            const id = event.id;
            const start = new Date(event.start.dateTime.value);
            const summary = event.summary;
            const description = event.description;
            const location = event.location || '';
            return new Event(id, start, summary, description, location);
        });
    }
}

export default Calendar;