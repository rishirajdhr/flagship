export type Auth = {
  token: string;
  username: string;
};

export type Flag = {
  id: number;
  name: string;
  description: string;
  enabled: boolean;
  projectId: number;
  owner: string;
  createdAt: string;
  updatedAt: string;
};

export type Project = {
  id: number;
  name: string;
  description: string;
  owner: string;
  createdAt: string;
  updatedAt: string;
};
