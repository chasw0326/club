import React, { createContext, useState } from 'react';

const initialState: any = {
  categories: ['test', 'test1', 'test2'],
  nickname: '',
};

const store = createContext(initialState);

const { Provider } = store;

const StoreProvider = ({ children }: { children: React.ReactChild }) => {
  const [globalCategory, setGlobalCategory] = useState(initialState);

  return (
    <Provider value={[globalCategory, setGlobalCategory]}>{children}</Provider>
  );
};

export { store, StoreProvider };
