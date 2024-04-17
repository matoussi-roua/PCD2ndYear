import { UserEntity } from './user_entity';

describe('Users', () => {
  it('create an instance', () => {
    expect(new UserEntity()).toBeTruthy();
  });
});
