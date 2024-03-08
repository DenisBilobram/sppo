#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <inttypes.h>

enum 
{
  MESSAGE_MAGIC = 0xAFAF, // magic signature value
  MAX_MESSAGE_LEN = 4096  // maximum message length
};
struct __attribute__((packed)) message_header 
{
  uint16_t magic;     // magic signature
  uint16_t type;      // type of the message
  uint16_t data_len;  // length of data
}; 
enum
{
  // maximum data length
  MAX_MESSAGE_DATA_LEN = MAX_MESSAGE_LEN - sizeof(struct message_header)
};
struct __attribute__((packed)) message 
{
  struct message_header header;
  // payload
  char data[MAX_MESSAGE_DATA_LEN];
};

int send(int fd, const struct message *msg)
{
  int msg_size;
  /* Check if the input data is not empty */
  if (fd < 0 || msg == NULL)
    return -1;
  /* Calculate the message size to send */
  msg_size = sizeof(struct message_header) + msg->header.data_len;
  /* Check if message payload size is valid */
  if (msg->header.data_len > MAX_MESSAGE_DATA_LEN)
    return -1;
  /* Write data to the output pipe (we assume it is ready) */
  if (write(fd, msg, msg_size) != msg_size)
    return -2;
  return 0;
}

int receive(int fd, struct message *msg)
{
  int msg_size;
  /* Check if the input data is not empty */
  if (fd < 0 || msg == NULL)
    return -1;
  /* Try to read header */
  msg_size = read(fd, &msg->header, sizeof(struct message_header));
  if (msg_size == 0)
    return 0;
  /* Check header magic */
  if (msg->header.magic != MESSAGE_MAGIC)
    return -2;
  /* Check if message has payload */
  if (msg->header.data_len > MAX_MESSAGE_DATA_LEN)
    return -2;
  if (msg->header.data_len > 0)
    msg_size += read(fd, &msg->data, msg->header.data_len);
  /* Return number of bytes read */
  return msg_size;
}