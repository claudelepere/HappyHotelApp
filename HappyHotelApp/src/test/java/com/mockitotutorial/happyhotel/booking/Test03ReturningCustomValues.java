package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import java.util.*;

class Test03ReturningCustomValues {
  
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
  void should_CountAvailablePlaces_When_OneRoomAvailable() {
    // given
    when(this.roomServiceMock.getAvailableRooms()).thenReturn(Collections.singletonList(new Room("Room 1", 5)));
    int expected = 5;

    // when
    int actual = bookingService.getAvailablePlaceCount();
    
    // then
    assertEquals(expected, actual);
  }

  @Test
  void should_CountAvailablePlaces_When_MultipleRoomAvailable() {
    // given
    List<Room> rooms = Arrays.asList(new Room("Room 1", 2), new Room("Room 2", 5));
    when(this.roomServiceMock.getAvailableRooms()).thenReturn(rooms);
    int expected = 7;

    // when
    int actual = bookingService.getAvailablePlaceCount();
    
    // then
    assertEquals(expected, actual);
  }

}
