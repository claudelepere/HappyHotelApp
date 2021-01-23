package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.*;
import java.time.LocalDate;

class Test07VerifyingBehaviour {
  
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
  void should_InvokePayment_When_Prepaid() {
    // given
    BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
    // when
    bookingService.makeBooking(bookingRequest);
    // then
    verify(paymentServiceMock, times(1)).pay(bookingRequest, 400.00);
    verifyNoMoreInteractions(paymentServiceMock);
  }

  @Test
  void should_NotInvokePayment_When_NotPrepaid() {
    // given
    BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);
    // when
    bookingService.makeBooking(bookingRequest);
    // then
    verify(paymentServiceMock, never()).pay(any(), anyDouble());
  }

}
