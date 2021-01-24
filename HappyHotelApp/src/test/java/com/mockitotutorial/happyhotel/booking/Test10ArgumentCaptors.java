package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import java.time.LocalDate;
import java.util.*;

class Test10ArgumentCaptors {
  
  private BookingService bookingService;
  private PaymentService paymentServiceMock;
  private RoomService roomServiceMock;
  private BookingDAO bookingDAOMock;
  private MailSender mailSenderMock;
  private ArgumentCaptor<Double> doubleCaptor;

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
    this.doubleCaptor = ArgumentCaptor.forClass(Double.class);
  }

  @AfterEach
  void tearDown() throws Exception {}

  @Test
  void should_PayCorrectPrice_When_InputOK() {
    // given
    BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
    // when
    bookingService.makeBooking(bookingRequest);
    // then
    verify(paymentServiceMock, times(1)).pay(eq(bookingRequest), doubleCaptor.capture());
    double capturedArgument = doubleCaptor.getValue();
    assertEquals(400.0, capturedArgument);
  }

  @Test
  void should_PayCorrectPrice_When_MultipleCalls() {
    // given
    BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
    BookingRequest bookingRequest2 = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 02), 2, true);
    List<Double> expectedValues = Arrays.asList(400.0, 100.0);
    // when
    bookingService.makeBooking(bookingRequest);
    bookingService.makeBooking(bookingRequest2);
    // then
    verify(paymentServiceMock, times(2)).pay(any(), doubleCaptor.capture());
    List<Double> capturedArguments = doubleCaptor.getAllValues();
    assertEquals(expectedValues, capturedArguments);
  }

}
