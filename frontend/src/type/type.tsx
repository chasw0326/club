type fetchState = {
  loading: boolean;
  data: any | clubItem[];
  error: any;
};

type category = {
  id : number;
  name : string;
  description : string;
}

type SignUp = {
  email: string;
  password: string;
  name: string;
  nickname: string;
  university: string;
  profile: any;
  introduction: string;
};

type clubInformation = {
  club: string;
  address: string;
  description: string;
  category : string;
};


type clubItem = {
  id: number | null;
  name: string;
  address: string;
  university: string;
  description: string;
  image_url: string;
  category: string;
  club_members: number | null;
};

type myClub = {
  club: string;
  address: string;
  universitiy: string;
  description: string;
};

type myPost = {
  nickname: string;
  title: string;
  content: string;
  createdAt: string;
};

type myComment = {
  title: string;
  nickname: string;
  content: string;
  createdAt: string;
};
const init = [
  {
    club: '',
    address: '',
    universitiy: '',
    description: '',

    nickname: '',
    title: '',
    content: '',

    commentTitle: '',
    commentNickname: '',
    commentContent: '',
  },
];

export type { clubInformation, fetchState, SignUp, clubItem, myClub, myPost, myComment, category };

export { init };
