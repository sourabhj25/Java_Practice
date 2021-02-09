import { TicketSystemPage } from './app.po';

describe('ticket-system App', () => {
  let page: TicketSystemPage;

  beforeEach(() => {
    page = new TicketSystemPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
