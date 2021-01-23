package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import java.time.LocalDate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

class Test09MockingVoidMethods {
  
  private BookingService bookingService;
  private PaymentService paymentServiceMock;
  private RoomService roomServiceMock;
  private BookingDAO bookingDAOMock;
  private MailSender mailSenderMock;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  @BeforeEach
  void setUp() throws Exception {
    this.paymentServiceMock = mock(PaymentService.class);
    this.roomServiceMock = mock(RoomService.class);
    this.bookingDAOMock = mock(BookingDAO.class);
    this.mailSenderMock = mock(MailSender.class);
    this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
  }

  @AfterEach
  void tearDown() throws Exception {}

  @Test
  void should_ThrowException_When_MailNotReady() {
    // given
    BookingRequest bookingRequest = new BookingRequest("2", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
    doThrow(new BusinessException()).when(mailSenderMock).sendBookingConfirmation(any());
    
    // when
    Executable executable = () -> bookingService.makeBooking(bookingRequest);
    
    // then
    assertThrows(BusinessException.class, executable);
  }

  @Test
  void should_NotThrowException_When_MailNotReady() {
    // given
    BookingRequest bookingRequest = new BookingRequest("2", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
//    doNothing().when(mailSenderMock).sendBookingConfirmation(any());
    
    // when
    bookingService.makeBooking(bookingRequest);
    
    // then
    // no exception thrown
  }

}
