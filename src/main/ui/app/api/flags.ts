import type { Auth, Flag } from "~/types";
import { withBase } from "./base";

export async function getAllFlagsForProject(projectId: string, auth: Auth) {
  const result = await fetch(withBase(`/api/projects/${projectId}/flags`), {
    headers: {
      Authorization: `Bearer ${auth.token}`,
    },
  });
  return result;
}

export interface UpdateFlagParams
  extends Partial<Pick<Flag, "description" | "enabled">> {
  flagId: string;
  projectId: string;
}

export async function updateFlagForProject(
  params: UpdateFlagParams,
  auth: Auth
) {
  const { flagId, projectId, ...body } = params;
  const result = await fetch(
    withBase(`/api/projects/${projectId}/flags/${flagId}`),
    {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${auth.token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(body),
    }
  );
  return result;
}
